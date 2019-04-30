FROM smasher164/javaalpine:prod
EXPOSE 8080
COPY web Instruction.java Main.java Parser.java Simulator.java State.java json.jar /
COPY static /static
ENTRYPOINT ["/web"]
