program forTest;

{$APPTYPE CONSOLE}

uses
  SysUtils;
var i, n: integer;
begin
  write('输入n的值');
  read(n);
  for i:=1 to n do
    writeln('delphi7');
  writeln('i的值为：' + inttostr(i));
  read(n);
  { TODO -oUser -cConsole Main : Insert code here }
end.
