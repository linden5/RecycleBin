program HaltTest;

{$APPTYPE CONSOLE}

uses
  SysUtils,Dialogs;

procedure M1;
begin
  Halt;
end;

procedure M2;
begin
  Writeln('M2');
  M1;
end;

begin
  ShowMessage('Starting program');
  M2;
  ShowMessage('Ending program');
  { TODO -oUser -cConsole Main : Insert code here }
end.
