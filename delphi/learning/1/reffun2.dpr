program reffun2;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TF = procedure(s:string = 'default');

procedure echol(s:string = 'delphi');
begin
  writeln(s);
end;

var
  F : TF;
begin
  F := echol;
  F;
  readln;
  { TODO -oUser -cConsole Main : Insert code here }
end.
