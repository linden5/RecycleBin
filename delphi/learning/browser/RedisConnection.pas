unit RedisConnection;

interface

uses
  Classes, Redis;

function getRedisConnection(aClient : ITCPClient = nil) : IRedisConnection;

implementation

uses
  SysUtils, RedisTCPClient;

const
  CrLf = #13#10;

type
  TRedisConnection = class (TInterfacedObject, IRedisConnection)
  private
    fHost : string;
    fPassword : string;
    fPort : integer;
    fUsername : string;
    fClient : ITCPClient;
    function getHost: string;
    function getPassword: string;
    function getPort: integer;
    function getUser: string;
    procedure setHost(const Value: string);
    procedure setPassword(const Value: string);
    procedure setPort(const Value: integer);
    procedure setUser(const Value: string);
    function tokenize(input : string) : TStringList;
    function getClient: ITCPClient;
    procedure setClient(const Value: ITCPClient);
  public
    property Host : string read getHost write setHost;
    property Port : integer read getPort write setPort;
    property Username : string read getUser write setUser;
    property Password : string read getPassword write setPassword;
    property TCPClient : ITCPClient read getClient write setClient;
    function ExecuteQuery(aQuery : string) : string;
    function Auth(aPassword: string): string;
  end;

function getRedisConnection(aClient : ITCPClient = nil) : IRedisConnection;
begin
  result := TRedisConnection.Create;
  result.Host := 'localhost';
  result.Port := 6379;
  if aClient = nil then aClient := getTCPClient;
  result.TCPClient := aClient;
end;

{ TRedisConnection }

function TRedisConnection.Auth(aPassword: string): string;
begin
  result := ExecuteQuery('AUTH ' + aPassword);
end;

function TRedisConnection.ExecuteQuery(aQuery: string): string;
var
  token : TStringList;
  s : AnsiString;
  i, count, bytes : integer;
begin
  try
    token := tokenize(aQuery);

    s := '*' + IntToStr(token.Count) + CrLf;
    for i := 0 to token.Count - 1 do
    begin
      s := s + '$' + IntToStr(length(token.Strings[i])) + CrLf;
      s := s + token.Strings[i] + CrLf;
    end;

    if not fClient.IsConnected then fClient.Connect(fHost, fPort);
    fClient.Write(s);

    result := fClient.ReadLn;
    case result[1] of
      '+' : delete(Result, 1, 1);
      '-' : raise RedisException.Create(result);
      ':' : delete(Result, 1, 1);
      '$' :
      begin
        delete(result,1,1);
        bytes := StrToIntDef(Result, -1);
        result := fClient.ReadBytes(bytes);
      end;
      '*' :
      begin
        delete(result,1,1);
        count := StrToInt(result);
        result := '';
        for i := 1 to count do
        begin
          s := fClient.ReadLn;
          delete(s,1,1);
          bytes := StrToInt(s);
          result := result + fClient.ReadBytes(bytes)+ CrLf;
          //fClient.ReadBytes(2); // Discard CrLf
        end;
        delete(result,length(result),2);
        //result := result + ']';
      end;
    end;
  finally

  end;
end;

function TRedisConnection.getClient: ITCPClient;
begin
  result := fClient;
end;

function TRedisConnection.getHost: string;
begin
  result := fHost;
end;

function TRedisConnection.getPassword: string;
begin
  result := fPassword;
end;

function TRedisConnection.getPort: integer;
begin
  result := fPort;
end;

function TRedisConnection.getUser: string;
begin
  result := fUsername;
end;

procedure TRedisConnection.setClient(const Value: ITCPClient);
begin
  fClient := Value;
end;

procedure TRedisConnection.setHost(const Value: string);
begin
  fHost := Value;
end;

procedure TRedisConnection.setPassword(const Value: string);
begin
  fPassword := Value;
end;

procedure TRedisConnection.setPort(const Value: integer);
begin
  fPort := Value;
end;

procedure TRedisConnection.setUser(const Value: string);
begin
  fUsername := Value;
end;

function TRedisConnection.tokenize(input: string): TStringList;
var
  s : string;

  procedure append(value : string);
  begin
    if value <> '' then
    begin
      Result.Add(value);
    end;
  end;

begin
  Result := TStringList.Create;
  s := '';
  while length(input) > 0 do
  begin
    if input[1] in [' ', #08, #09, #10, #12, #13] then
    begin
      append(s);
      s := '';
    end
    else
    begin
      s := s + input[1];
    end;
    delete(input,1,1);
  end;
  append(s);
  s := '';
end;

end.


