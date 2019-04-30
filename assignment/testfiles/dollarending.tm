var x
loop:
read x
skipif x != ' '
jump exit
move 1
jump loop
exit:
write '$'