program proc;

{$APPTYPE CONSOLE}

uses
  SysUtils;

procedure LinkStr;
begin
end;

var
  F, Q : procedure;
  v2: String;
begin
  F := LinkStr;
  writeln(integer(@@F));
  writeln(integer(@F));
  writeln(integer(@LinkStr));
  readln(v2);
  { TODO -oUser -cConsole Main : Insert code here }
end.
