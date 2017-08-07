program funTest;

{$APPTYPE CONSOLE}

uses
  SysUtils, Forms;

function MakeStr(const args : array of const) : string;
var
  i: integer;
begin
  result := '';
  for i := 1 to High(args) do
    with args[i] do
      case VType of
        vtInteger: result := result + inttostr(VInteger);
        vtBoolean: result := result + booltostr(VBoolean);
        vtChar:    result := result + VChar;
        vtExtended:result := result + floattostr(VExtended^);
        vtString:  result := result + VString^;
        vtPChar:   result := result + VPChar;
        vtObject:  result := result + VObject.ClassName;
        vtClass:   result := result + VClass.ClassName;
        vtAnsiString: result := result + string(VAnsiString);
       { vtUnicodeString result := result + string(VUnicodeString); }
        vtCurrency:  result := result + currtostr(VCurrency^);
        vtVariant: result := result + string(VVariant^);
        vtInt64:   result := result + inttostr(VInt64^);
end;

var
  s : string;
begin
  s := MakeStr(['test', 100, ' ', true, 3.14159, TForm]);
 writeln(s);
  read(s);
  { TODO -oUser -cConsole Main : Insert code here }
end.

