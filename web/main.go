package main

import (
	"encoding/json"
	"flag"
	"html/template"
	"log"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
)

var static string
var simulator string

func init() {
	flag.StringVar(&static, "static", "/static", "path to static file resources")
	flag.StringVar(&simulator, "simulator", "/", "path to simulator")
}

func stat(f func(http.ResponseWriter, *http.Request) error) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		if err := f(w, r); err != nil {
			log.Println(err)
			http.Error(w, "500 Internal Server Error", http.StatusInternalServerError)
		}
	}
}

type sim struct {
	static    string
	simulator string
	tmpl      *template.Template
}

type result struct {
	Err    string  `json:"err"`
	States []state `json:"states"`
}

type state struct {
	IP    int               `json:"ip"`
	INP   int               `json:"inp"`
	Tape  string            `json:"tape"`
	Decls map[string]string `json:"decls"`
}

func (s *sim) init() {
	var files []string
	filepath.Walk(s.simulator, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return nil
		}
		if _, err := os.Lstat(info.Name()); err != nil {
			return nil
		}
		if filepath.Ext(path) == ".java" {
			files = append(files, path)
		}
		return nil
	})
	args := append([]string{"-cp", s.simulator + ":" + filepath.Join(s.simulator, "json.jar")}, files...)
	cmd := exec.Command("/usr/bin/javac", args...)
	if err := cmd.Run(); err != nil {
		log.Fatalf("failed to compile simulator %q\n", err)
	}
}

func (s *sim) home(w http.ResponseWriter, r *http.Request) error {
	if r.URL.Path != "/" {
		http.NotFound(w, r)
		return nil
	}
	s.tmpl.ExecuteTemplate(w, "home.tmpl", nil)
	return nil
}

func (s *sim) apiExec(w http.ResponseWriter, r *http.Request) error {
	args := append([]string{"-cp", s.simulator + ":" + filepath.Join(s.simulator, "json.jar")}, "Main")
	cmd := exec.Command("/usr/bin/java", args...)
	cmd.Stdin = r.Body
	out, err := cmd.Output()
	if err != nil {
		return err
	}
	var res result
	if err := json.Unmarshal(out, &res); err != nil {
		log.Fatalln(err)
	}
	if len(res.Err) != 0 {
		w.WriteHeader(http.StatusBadRequest)
	}
	if _, err := w.Write(out); err != nil {
		return err
	}
	return nil
}

func main() {
	flag.Parse()
	log.SetPrefix("turing-web: ")
	log.SetFlags(0)
	tmpl := template.Must(template.ParseFiles(
		filepath.Join(static, "home.tmpl"),
	))
	m := http.NewServeMux()
	s := &sim{
		static:    static,
		simulator: simulator,
		tmpl:      tmpl,
	}
	s.init()
	m.HandleFunc("/", stat(s.home))
	m.HandleFunc("/api/exec", stat(s.apiExec))
	m.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir(static))))
	m.HandleFunc("/favicon.ico", func(w http.ResponseWriter, r *http.Request) {
		w.Write(nil)
	})
	log.Fatal(http.ListenAndServe(":8080", m))
}
