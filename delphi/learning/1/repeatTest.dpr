program repeatTest;

{$APPTYPE CONSOLE}

uses
  SysUtils;

var
  n, sum, i:integer;
begin
  write('请输入一个1至100间的数：');
  read(n);
  sum := 1;
  i := 1;
  repeat
    i:=i+1;
    sum:= sum + i;
  until i = n;
  writeln(inttostr(sum));
  read(i);
  { TODO -oUser -cConsole Main : Insert code here }
end.
