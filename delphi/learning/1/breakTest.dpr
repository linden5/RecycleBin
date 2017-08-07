program breakTest;

{$APPTYPE CONSOLE}

uses
  SysUtils;
var
  i: integer;
  len: integer;
  ch:char;
  str:string;
begin
  str:='ABCDEFGHIJKLMN';
  len := Length(str);
  for i := 0 to len do
  begin
    ch := str[i];
    if ch = 'H' then
      break;
    writeln(ch);
  end;
  readln(ch);
  { TODO -oUser -cConsole Main : Insert code here }
end.
