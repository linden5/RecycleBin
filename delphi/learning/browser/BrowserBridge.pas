unit BrowserBridge;

interface

uses
  Dialogs, SysUtils, RedisUtils;

type
  TBrowserBridge = class
    class function  GetLoginSequence: string;
    class procedure OpenBrowser(URL: string; MinIEVersion: Integer);
    class function CreateSequence: string;
    class function ClearSequence: string;
    class procedure SetRedisHostAndPort(Host: string; Port: Integer);
    class procedure SetRedis(RedisUtils: TRedisUtils);
  end;

implementation

uses
  Classes, StrUtils, Variants, BrowserUtils, RandomSequence;

var
  Redis: TRedisUtils;
  LoginSequence: string;

class function TBrowserBridge.GetLoginSequence: string;
begin
  Result := LoginSequence;
end;
  
{ 在非某个版本的IE中打开默认浏览器，如果默认浏览器是IE且版本号小于某一个值，提示安装火狐 }
class procedure TBrowserBridge.openBrowser(URL: string; MinIEVersion: Integer);
var
  BrowserName: string;
  BrowserVersion: string;
  IsIE: Boolean;
  VersionToken: TStrings;
begin
  BrowserName := TBrowserUtils.GetDefaultBrowser;
  IsIE := AnsiContainsStr(BrowserName, 'iexplore');
  if IsIE then
  begin
    BrowserVersion := TBrowserUtils.GetIEVersion;
    VersionToken := TStringList.Create;
    VersionToken.Delimiter := '.';
    VersionToken.DelimitedText := BrowserVersion;
    if StrToInt(VersionToken[0]) < MinIEVersion then
    begin
      ShowMessage('您的IE浏览器版本过低，建议安装最新版火狐浏览器并设置为默认浏览器，之后再使用该功能');
      TBrowserUtils.OpenInDefaultBrowser('http://www.firefox.com.cn/');
    end
  end
  else
    TBrowserUtils.OpenInDefaultBrowser(URL);
end;

class function TBrowserBridge.createSequence: string;
begin
  LoginSequence := TRandomSequence.Generate;
  if LoginSequence <> '' then
    Result := Redis.SetToRedis(LoginSequence, 'true', 10 * 60 * 60);
end;

class function TBrowserBridge.clearSequence: string;
begin
  if LoginSequence <> '' then
  begin
    Result := Redis.DelFromRedis(LoginSequence);
    LoginSequence := '';
  end;
end;

class procedure TBrowserBridge.SetRedisHostAndPort(Host: string; Port: Integer);
begin
  if Redis <> nil then
    Redis.Destroy;
    
  Redis := TRedisUtils.Create(Host, Port);
end;

class procedure TBrowserBridge.SetRedis(RedisUtils: TRedisUtils);
begin
  if Redis <> nil then
    Redis.Destroy;
    
  Redis := RedisUtils;
end;

end.



