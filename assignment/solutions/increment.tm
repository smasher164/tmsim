var curr
loop:
read curr
skipif curr != '1'
jump one
write '1'
jump end
one:
write '0'
move 1
jump loop
end: