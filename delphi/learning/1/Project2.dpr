program Project1;

{$APPTYPE CONSOLE}

uses
  SysUtils;

var
  i:integer;
label 1,2;
begin
  { TODO -oUser -cConsole Main : Insert code here }
  1:writeln('«Î ‰»Îiµƒ÷µ:');
    readln(i);
    if i <> 0 then
      goto 1;
    exit;
end.
