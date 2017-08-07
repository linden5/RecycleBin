program anonymousFun;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TFun = procedure(s:string);
procedure Fa(s:string; Fun: TFun);
begin
  Fun(s);
end;

var
  F:TFun;
begin
  F := procedure(s:string)
      begin
        writeln(s);
      end;
  Fa('delphi2000', F);

  readln;
  { TODO -oUser -cConsole Main : Insert code here }
end.
 