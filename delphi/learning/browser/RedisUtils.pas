unit RedisUtils;

interface

uses Redis;

type
  TRedisUtils = class
  private
    fConnection: IRedisConnection;
  public
    constructor Create(Host: string; Port: Integer); overload;
    function SetToRedis(Key: string; Value: string;TTL: Integer): string;
    function GetFromRedis(Key: string): string;
    function DelFromRedis(Key: string): string;
  end;

implementation

uses
  SysUtils;


{指定redis端口}
constructor TRedisUtils.Create(Host: string; Port: Integer);
begin
  fConnection := TRedis.getConnection(Host, Port)
end;


{实际上是setex，指定了存储时间}
function TRedisUtils.SetToRedis(Key: string; Value: string; TTL: Integer): string;
var
  Query : string;
begin
  Query := 'setex ' + Key + ' ' + IntToStr(TTL) + ' ' + Value;
  Result := fConnection.ExecuteQuery(Query);
end;

{从redis取值}
function TRedisUtils.GetFromRedis(Key: string): string;
var
  Query : string;
begin
  Query := 'get ' + Key;
  Result := fConnection.ExecuteQuery(Query);
end;

{从redis删除}
function TRedisUtils.DelFromRedis(Key: string): string;
var
  Query : string;
begin
  Query := 'del ' + Key;
  Result := fConnection.ExecuteQuery(Query);
end;

end.
