unit Browser;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

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
    RandomStrButton: TButton;
    RedisDelButton: TButton;
    GroupBox1: TGroupBox;
    GroupBox2: TGroupBox;
    LoginButton: TButton;
    LogoutButton: TButton;
    AddressButton: TButton;
    Label5: TLabel;
    procedure LauchBrowserButtonClick(Sender: TObject);
    procedure GetDefaultBrowserButtonClick(Sender: TObject);
    procedure IEVersionButtonClick(Sender: TObject);
    procedure SetToRedisButtonClick(Sender: TObject);
    procedure GetFromRedisButtonClick(Sender: TObject);
    procedure RandomStrButtonClick(Sender: TObject);
    procedure RedisDelButtonClick(Sender: TObject);
    procedure LoginButtonClick(Sender: TObject);
    procedure LogoutButtonClick(Sender: TObject);
    procedure AddressButtonClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses BrowserUtils, RedisUtils, RandomSequence, BrowserBridge;

{$R *.dfm}


var
  Redis: TRedisUtils;
  
{浏览器打开新页面}
procedure TForm1.LauchBrowserButtonClick(Sender: TObject);
var
  URLToOpen: string;
begin
  URLToOpen := 'http://' + URLEdit.Text + '?usercode=' + SetKey.Text;
  // The following commented code works, leave it here undeleted as a referrence
  //ShellExecute(Application.Handle, nil, 'http://cn.bing.com', nil, nil, SW_SHOWNORMAL);
  TBrowserUtils.OpenInDefaultBrowser(URLToOpen);
end;

{取得默认浏览器}
procedure TForm1.GetDefaultBrowserButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserUtils.GetDefaultBrowser);
end;

{取得IE版本}
procedure TForm1.IEVersionButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserUtils.GetIEVersion);
end;

procedure TForm1.SetToRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;
  RedisResult: string;
begin
  Key := SetKey.Text;
  Value := SetValue.Text;

  RedisResult := Redis.SetToRedis(Key, Value, 10 * 60 * 60);
  ShowMessage(RedisResult);
end;

procedure TForm1.GetFromRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;

begin
  Key := SetKey.Text;

  Value := Redis.GetFromRedis(Key);
  GetValue.Text := Value;

end;

procedure TForm1.RandomStrButtonClick(Sender: TObject);
var
  str : string;
begin
  str := TRandomSequence.Generate;
  ShowMessage(str);
  SetKey.Text := str;
end;

procedure TForm1.RedisDelButtonClick(Sender: TObject);
var
  Key: string;
  RedisResult: string;
begin
  Key := SetKey.Text;

  RedisResult := Redis.DelFromRedis(Key);
  ShowMessage(RedisResult);
end;

{实际中用到的大概长这样}
{登录}
procedure TForm1.LoginButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserBridge.createSequence);
end;

{登出}
procedure TForm1.LogoutButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserBridge.clearSequence);
end;

{打开页面}
procedure TForm1.AddressButtonClick(Sender: TObject);
var
  URL: string;
begin
  URL := 'http://localhost:8080/btejump?usercode=' + TBrowserBridge.GetLoginSequence;
  TBrowserBridge.openBrowser(URL, 10);
end;

{初始化，也可以调用TBrowserBridge.SetRedisHostAndPort方法}
initialization
  Redis := TRedisUtils.Create('localhost', 6379);
  TBrowserBridge.SetRedis(Redis);

end.
