program sumInt;

{$APPTYPE CONSOLE}

uses
  SysUtils;

var
  n, i: 1..100;
  sum:integer;
begin
  write('请输入一个1至100间的数字');
  read(n);
  sum := 0; //将sum值初始化为0
  i := 1;
  while i <= n do
  begin
    sum := sum + i;
    i := i+1;
  end;
  writeln(inttostr(sum));
  read;
  { TODO -oUser -cConsole Main : Insert code here }
end.
