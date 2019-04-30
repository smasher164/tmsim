var x
var res
read x
write '0'
read res
write x
skipif x != ' '
jump beg
skipif x != 'a'
jump beg
skipif x != 'b'
jump beg
jump halt
beg:
write '$'
move 1
skipif x != 'b'
jump L2
L1:
read x
skipif x == 'a'
jump L2
write ' '
move 1
jump L1
L2:
read x
skipif x == 'b'
jump L3
write ' '
move 1
jump L2
L3:
skipif x == ' '
jump reject
jump accept
reject:
write ' '
move 1
read x
skipif x == ' '
jump reject
jump end
accept:
write '1'
read res
write ' '
jump end
end:
read x
skipif x != '$'
jump halt
move -1
jump end
halt:
write res