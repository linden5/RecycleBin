program constProc;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  pi = ^integer;

procedure var_sample(const i : pi);
begin
  i^ := 2 * i^;
  writeln(i^);
end;

var
  n : integer;
begin
  n := 9;
  var_sample(@n);
  writeln(n);
  readln(n);
  { TODO -oUser -cConsole Main : Insert code here }
end.
 