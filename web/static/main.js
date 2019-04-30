let ln = document.querySelector('line-numbers')
let ta = document.querySelector('textarea')
let tape = document.querySelector('tape')
let inIP = document.querySelector('#in-ip')
let inINP = document.querySelector('#in-inp')
let inTape = document.querySelector('#in-tape')
let btnRun = document.querySelector('#btn-run')
let btnStop = document.querySelector('#btn-stop')
let btnStep = document.querySelector('#btn-step')
let error = document.querySelector('error')
let btnErr = document.querySelector('#btn-err')
let message = document.querySelector('#message')

let prev = 1

let pos = 0
let state = {
    step: false,
    ip: 0,
    inp: 0,
    tape: "",
    prog: "",
    decls: {},
}

let rstates = null

let highlight = (ip) => {
    let divs = ln.querySelectorAll('div')
    if (ip >= divs.length) {
        return
    }
    let div = divs[ip]
    div.style.margin = "0 -0.5em"
    div.style.paddingRight = "0.5em"
    div.style.backgroundColor = "#999e80"
}

let updateLineNumbers = (force) => {
    let n = ta.value.split(/\n/).length
    if (n > prev || force) {
        prev = n
        let nums = ""
        for(let i = 0; i < n; i++) {
            nums += `<div>${i.toString()}</div>`
        }
        ln.innerHTML = nums
    }
}

let updateUI = () => {
    updateLineNumbers(true)
    highlight(state.ip)
    inIP.value = "" + state.ip
    inINP.value = "" + state.inp
    inTape.value = state.tape
    let cells = ""
    for(let i = 0; i < state.tape.length; i++) {
        if (state.inp == i) {
            cells += `<div style="background-color: #f8ffd7;">${state.tape[i]}</div>`
        } else {
            cells += `<div>${state.tape[i]}</div>`
        }
    }
    if (state.tape.length < 14) {
        let n = 14 - state.tape.length
        for(let i = 0; i < n; i++) {
            cells += `<div></div>`
        }
    }
    tape.innerHTML = cells
}

let step = (states) => {
    if (pos < states.length) {
        state.ip = states[pos].ip
        state.inp = states[pos].inp
        state.tape = states[pos].tape
        state.decls = states[pos].decls
        pos++
    } else {
        state.step = false
    }
    updateUI()
}

let run = (states) => {
    state.step = true
    state.ip = states[0].ip
    state.inp = states[0].inp
    state.tape = states[0].tape
    state.decls = states[0].decls
    setTimeout(function loop(){
        step(states)
        if(!state.step) {
            return
        }
        setTimeout(loop, 500)
    }, 500)
}

ta.addEventListener('input', () => updateLineNumbers(false))
ta.addEventListener('input', () => ta.style.height = ta.scrollHeight + "px")

let ripple = (button, ev, color) => {
    let rips = button.querySelectorAll(".ripple")
    for (let i = 0; i < rips.length; i++) {
        rips[i].remove()
    }
    let rect = button.getBoundingClientRect()
    button.insertAdjacentHTML("afterbegin", "<span class='ripple'></span>")
    let w = rect.width
    let h = rect.height
    if (w >= h)
        h = w
    else
        w = h
    let x = (ev.pageX || (rect.x + rect.width / 2)) - rect.left - w / 2
    let y = (ev.pageY || (rect.y + rect.height / 2)) - rect.top - h / 2
    let rip = button.querySelector(".ripple")
    rip.style.background = color
    rip.style.width = w + 'px'
    rip.style.height = h + 'px'
    rip.style.top = y + 'px'
    rip.style.left = x + 'px'
    rip.classList.add('rippleEffect')
}

let displayError = (err) => {
    message.textContent = err
    error.classList.remove('fadeOut')
    error.classList.add('fadeScale')
}

btnRun.addEventListener('click', (e) => {
    btnRun.blur()
    ripple(btnRun, e, 'rgba(103, 121, 83, 0.4)')
    state.ip = parseInt(inIP.value, 10)
    state.inp = parseInt(inINP.value, 10)
    state.tape = inTape.value
    state.prog = ta.value
    let m = {
        input: state.tape,
        program: state.prog,
        ip: state.ip,
        inp: state.inp,
        labels: {},
        decls: state.decls,
    }
    var req = new XMLHttpRequest()
    req.responseType = 'json';
    req.addEventListener('load', (ev)=>{
        if(ev.target.status != 200) {
            if (ev.target.response && ev.target.response.err != "") {
                displayError(ev.target.response.err)
            } else {
                displayError("Something went wrong")
            }
        } else {
            pos = 0
            rstates = ev.target.response.states
            run(rstates)
        }
    })
    req.open('POST', '/api/exec')
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8")
    req.send(JSON.stringify(m))
})

btnStep.addEventListener('click', (e) => {
    btnStep.blur()
    ripple(btnStep, e, 'rgba(103, 121, 83, 0.4)')
    let ip = parseInt(inIP.value, 10)
    let inp = parseInt(inINP.value, 10)
    let tv = inTape.value
    let tav = ta.value
    let modified = false
    if (ip != state.ip) {
        modified = true
        state.ip = ip
    }
    if (inp != state.inp){
        modified = true
        state.inp = inp
    }
    if (tv != state.tape) {
        modified = true
        state.tape = tv
    }
    if (tav != state.prog) {
        modified = true
        state.prog = tav
    }
    if (modified) {
        let m = {
            input: state.tape,
            program: state.prog,
            ip: state.ip,
            inp: state.inp,
            labels: {},
            decls: state.decls,
        }
        var req = new XMLHttpRequest()
        req.responseType = 'json';
        req.addEventListener('load', (ev)=>{
            if(ev.target.status != 200) {
                if (ev.target.response && ev.target.response.err != "") {
                    displayError(ev.target.response.err)
                } else {
                    displayError("Something went wrong")
                }
            } else {
                pos = 0
                rstates = ev.target.response.states
                step(rstates)
            }
        })
        req.open('POST', '/api/exec')
        req.setRequestHeader("Content-Type", "application/json;charset=UTF-8")
        req.send(JSON.stringify(m))
    } else {
        step(rstates)
    }
})

btnStop.addEventListener('click', (e) => {
    btnStop.blur()
    ripple(btnStop, e, 'rgba(103, 121, 83, 0.4)')
    state.step = false
})

btnErr.addEventListener('click', (e) => {
    btnErr.blur()
    ripple(btnErr, e, 'rgba(200, 164, 21, .2)')
    error.classList.remove('fadeScale')
    error.classList.add('fadeOut')
})