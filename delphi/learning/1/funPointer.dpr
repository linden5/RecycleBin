program funPointer;

{$APPTYPE CONSOLE}

uses
  SysUtils;

function LinkStr( s : string) : string;
begin
  result := '参数s的值为：' + s;
end;

var
  F : function( s : string ) : string;
  Str : string;

begin
  F := LinkStr;
  Str := F('This is Delphi2010');
  writeln(LinkStr('this is Delphi2010'));
  writeln(Str);
  readln(Str);
  { TODO -oUser -cConsole Main : Insert code here }
end.
 