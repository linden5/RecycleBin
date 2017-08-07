program passAddr;

{$APPTYPE CONSOLE}

uses
  SysUtils;

procedure var_sample(i:integer);
begin
  i:= 2*i;
  writeln(i);
end;

var
  n:integer;
begin
  n:=9;
  var_sample(n);
  writeln(n);
  readln(n);
  { TODO -oUser -cConsole Main : Insert code here }
end.
