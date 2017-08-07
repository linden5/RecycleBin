program caseTest;

{$APPTYPE CONSOLE}

uses
  SysUtils;

var
  N: integer;
  
begin
  { TODO -oUser -cConsole Main : Insert code here }
  write('请输入一个1至4间的数字；');
  read(n);
  case N of
    1:writeln('*');
    2:writeln('**');
    3:writeln('***');
    4:writeln('****');
  else
    writeln('所输的数字不在1至4之间');
  end;
  readln;
end.
