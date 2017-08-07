program emptyArg;

{$APPTYPE CONSOLE}

uses
  SysUtils;

procedure var_sample(out s : string);
begin
// 什么都不做
end;

var
  str : string;
begin
  str := 'delphi';
  writeln(str);//显示delphi
  var_sample(str);
  writeln(str);// 不显示任何内容
  readln(str);
  { TODO -oUser -cConsole Main : Insert code here }
end.
