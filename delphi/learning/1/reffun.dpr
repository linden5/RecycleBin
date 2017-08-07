program reffun;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TFun = reference to function(x : integer) : integer;

function Fun(x : integer) : integer;
begin
 writeln( x + 100 );
end;

var
  F: TFun;
  s: String;
begin
  F := Fun;
  F(12);
  readln(s);
  { TODO -oUser -cConsole Main : Insert code here }
end.
 