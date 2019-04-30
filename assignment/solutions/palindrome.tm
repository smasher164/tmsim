var curr
read curr
skipif curr == ' '
jump beg
write '1'
jump halt

beg:
    var a
    var b
    read a
    write '$'

L:
    move 1
    read curr
    skipif curr == ' '
    jump L
    move -1
    read curr
    skipif curr == '$'
    jump main
    write '1'
    jump halt

main:
    read b
    skipif a == b
    jump reject
    write ' '

L2:
    move -1
    read curr
    skipif curr == '$'
    jump L2
    move 1

outer:
    read a
    write ' '

L3:
    move 1
    read curr
    skipif curr == ' '
    jump L3
    move -1
    read b
    write ' '

    skipif b != ' '
    jump accept
    skipif a == b
    jump reject

L4:
    move -1
    read curr
    skipif curr == ' '
    jump L4
    move 1
    jump outer

accept:
    move -1
    read curr
    skipif curr == '$'
    jump accept
    write '1'
    jump halt

reject:
    write ' '
    move -1
    read curr
    skipif curr == '$'
    jump reject
    write '0'

halt: