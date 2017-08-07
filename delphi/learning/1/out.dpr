program out;

{$APPTYPE CONSOLE}

uses
  SysUtils;

function sample1(s : string) : integer;
begin
  result := length(s); // ·µ»Ø×Ö·û´®³¤¶È
end;

procedure sample2(s : string; out len : integer);
begin
  len := length(s);
end;

var
  i, n : integer;
  s : string;

begin
  s := 'delphi';
  i := sample1(s);
  sample2(s, n);
  writeln(inttostr(i) + ' : ' + inttostr(n));
  readln(s);
  { TODO -oUser -cConsole Main : Insert code here }
end.
