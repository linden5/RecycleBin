program variant;

{$APPTYPE CONSOLE}

uses
  SysUtils;

var
  v:variant;
begin
  v := 'Delphi';
  if TVarData(v).VType = varUString then
    writeln('v中的实际类型为UnicodeString');
  { TODO -oUser -cConsole Main : Insert code here }
end.
 