program BrowserProject;

uses
  Forms,
  Browser in 'Browser.pas' {Form1},
  BrowserUtils in 'BrowserUtils.pas',
  RedisUtils in 'RedisUtils.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
