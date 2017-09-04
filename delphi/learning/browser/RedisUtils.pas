unit RedisUtils;

interface

uses Redis;

type
  TRedisUtils = class
  private
    fConnection: IRedisConnection;
  public
    constructor Create; overload;
    constructor Create(Host: string; Port: Integer); overload;
    destructor Destroy;
    function SetToRedis(Key: string; Value: string): string;
    function GetFromRedis(Key: string): string;
    function DelFromRedis(Key: string): string;
  end;

implementation

uses
  SysUtils;

constructor TRedisUtils.Create;
begin
  fConnection := TRedis.getConnection('localhost', 6379);
end;

constructor TRedisUtils.Create(Host: string; Port: Integer);
begin
  fConnection := TRedis.getConnection(Host, Port)
end;

destructor TRedisUtils.Destroy;
begin
  fConnection._Release;
end;

function TRedisUtils.SetToRedis(Key: string; Value: string): string;
var
  Seconds: Integer;
  Query : string;
begin
  Seconds := 12 * 60 * 60;   // 定义key的存储时间
  Query := 'setex ' + Key + ' ' + IntToStr(Seconds) + ' ' + Value;
  Result := fConnection.ExecuteQuery(Query);
end;

function TRedisUtils.GetFromRedis(Key: string): string;
var
  Query : string;
begin
  Query := 'get ' + Key;
  Result := fConnection.ExecuteQuery(Query);
end;

function TRedisUtils.DelFromRedis(Key: string): string;
var
  Query : string;
begin
  Query := 'del ' + Key;
  Result := fConnection.ExecuteQuery(Query);
end;

end.
