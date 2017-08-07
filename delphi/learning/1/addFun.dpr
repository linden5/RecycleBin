program addFun;

{$APPTYPE CONSOLE}

uses
  SysUtils;

function add(x, y : integer) : integer;
var
  a : string;
begin
  result := x + y;
end;

var
  s : integer;
begin
  s := add(2, 3);
  writeln(s);
  readln(s);
  { TODO -oUser -cConsole Main : Insert code here }
end.
