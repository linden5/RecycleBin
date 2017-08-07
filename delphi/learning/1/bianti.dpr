program bianti;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TRec = record
    s:string;
  case Integer of
    1:(f1:integer;
       f2:String[4]);
    2,6,8:(f3:string[8])
  end;
  
var
  Rec:TRec;
begin
  { TODO -oUser -cConsole Main : Insert code here }
  Rec.s := '5';
  Rec.f1 := 4;
  Rec.f2 := 'ABCD';
  Rec.f3 := 'Delphi32';
  writeln(Rec.f2);
  readln;
end.
