program Project1;
{$APPTYPE CONSOLE}
uses
  SysUtils, Dialogs;

var
  Money, Weight: Double;
  str : string;
begin
  Write('请输入货物重量');
  Read(Weight);
  if Weight <= 50 then
    Money := Weight * 0.25
  else
  begin
    if Weight <= 100 then
      Money := (Weight - 50) * 0.35 + 50 * 0.25
    else
      if Weight > 100 then
        Money := (Weight - 100) * 0.45 + 50 * 0.35 + 50 * 0.25;
  end;
  Showmessage('运费的金额为：' + FloatToStr(Money) + '元');
end.
