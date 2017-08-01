program Record1;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  TStd = record // 声明记录类型
    Name:string;
    Grade:integer;
end;

var
  A,C:TStd;
  B:record
    Name:string;
    Grade:integer;
end;
begin
  { TODO -oUser -cConsole Main : Insert code here }
  B.Name := 'BName';
  A := TStd(B);
  C.Name := B.Name;
  writeln(A.Name);
  writeln(C.Name);
  readln;
end.
