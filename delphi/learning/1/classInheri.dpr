program classInheri;

{$APPTYPE CONSOLE}

uses
  SysUtils;

type
  T1 = class
    procedure F1; virtual;
  end;
  T2 = class(T1)
    procedure F1; override;
  end;

procedure T1.F1;
begin
  writeln('this is T1.F1');
end;

procedure T2.F1;
begin
  writeln('this is T2.F1');
end;

var
  O1 : T1;
  O2 : T2;
begin
  O1 := T1.Create;
  O2 := T2.Create;
  O1.F1;
  O2.F1;
  O1 := O2;
  O1.F1;
  readln;
end.
 