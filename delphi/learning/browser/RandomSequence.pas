unit RandomSequence;

interface

type
  TRandomSequence = class
    class function Generate : string;
  end;

implementation

class function TRandomSequence.Generate;
Const
  SourceStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
var
  i : Integer;
  str: string;
begin
  for i := 1 to 32 do
    str := str + SourceStr[Random(62) + 1];
  Result := str;
end;

end.
