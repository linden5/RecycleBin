program functionVARAR;
{$APPTYPE CONSOLE}
uses
  Forms;

function AddData(x : integer; y : real) : integer;
begin
  result := 2 * x;
end;

var
  F, Q: function(x : integer; y : real) : integer;
begin
  f := AddData;
  q := f;
end.
