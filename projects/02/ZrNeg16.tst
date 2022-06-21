load ZrNeg16.hdl,
output-file ZrNeg16.out,
compare-to ZrNeg16.cmp,
output-list in%B1.16.1 z%B1.1.1 n%B1.1.1 out%B1.16.1;


set in %B0001110001110001,
set z 0,
set n 0,
eval,
output;

set in %B0001110001110001,
set z 1,
set n 0,
eval,
output;

set in %B0001110001110001,
set z 0,
set n 1,
eval,
output;

set in %B0001110001110001,
set z 1,
set n 1,
eval,
output;