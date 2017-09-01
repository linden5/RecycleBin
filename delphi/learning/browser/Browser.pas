unit Browser;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, RedisUtils;

type
  TForm1 = class(TForm)
    LauchBrowserButton: TButton;
    URLEdit: TEdit;
    GetDefaultBrowserButton: TButton;
    IEVersionButton: TButton;
    SetKey: TEdit;
    SetValue: TEdit;
    SetToRedisButton: TButton;
    GetFromRedisButton: TButton;
    GetKey: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    GetValue: TEdit;
    procedure LauchBrowserButtonClick(Sender: TObject);
    procedure GetDefaultBrowserButtonClick(Sender: TObject);
    procedure IEVersionButtonClick(Sender: TObject);
    procedure SetToRedisButtonClick(Sender: TObject);
    procedure GetFromRedisButtonClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses BrowserUtils;

{$R *.dfm}


procedure TForm1.LauchBrowserButtonClick(Sender: TObject);
var
  URLToOpen: string;
begin
  URLToOpen := 'http://' + URLEdit.Text;
  ShowMessage(URLToOpen);
  // The following commented code works, leave it here undeleted as a referrence
  //ShellExecute(Application.Handle, nil, 'http://cn.bing.com', nil, nil, SW_SHOWNORMAL);
  TBrowserUtils.OpenInDefaultBrowser(URLToOpen);
end;

procedure TForm1.GetDefaultBrowserButtonClick(Sender: TObject);
var
  DefaultBrowser: string;
begin
  DefaultBrowser := TBrowserUtils.GetDefaultBrowser;
  ShowMessage(DefaultBrowser);
end;

procedure TForm1.IEVersionButtonClick(Sender: TObject);
var
  IEVersion: string;
begin
  IEVersion := TBrowserUtils.GetIEVersion;
  ShowMessage(IEVersion);
end;

procedure TForm1.SetToRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;
  Redis: TRedisUtils;
  RedisResult: string;
begin
  Key := SetKey.Text;
  Value := SetValue.Text;

  Redis := TRedisUtils.Create();
  RedisResult := Redis.SetToRedis(Key, Value);
  ShowMessage(RedisResult);
end;

procedure TForm1.GetFromRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;
  Redis: TRedisUtils;
begin
  Key := SetKey.Text;

  Redis := TRedisUtils.Create();
  Value := Redis.GetFromRedis(Key);
  GetValue.Text := Value;

end;

end.
