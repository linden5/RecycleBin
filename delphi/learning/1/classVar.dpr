program classVar;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TBall = class(TObject)
  class var
    shape : string;
    name  : string;
  end;

var
  obj : TBall;
begin
  TBall.shape := 'Round';
  TBall.name := 'Ball';
  obj := TBall.Create;
  writeln(obj.shape);
  readln;
  { TODO -oUser -cConsole Main : Insert code here }
end.
 