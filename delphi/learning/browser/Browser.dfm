object Form1: TForm1
  Left = 207
  Top = 118
  Width = 928
  Height = 480
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 112
    Top = 264
    Width = 27
    Height = 17
    Caption = 'Value'
  end
  object Label2: TLabel
    Left = 112
    Top = 240
    Width = 18
    Height = 13
    Caption = 'Key'
  end
  object Label3: TLabel
    Left = 112
    Top = 344
    Width = 18
    Height = 13
    Caption = 'Key'
  end
  object Label4: TLabel
    Left = 112
    Top = 376
    Width = 27
    Height = 13
    Caption = 'Value'
  end
  object LauchBrowserButton: TButton
    Left = 280
    Top = 104
    Width = 131
    Height = 25
    Caption = 'Open in Default Browser'
    TabOrder = 0
    OnClick = LauchBrowserButtonClick
  end
  object URLEdit: TEdit
    Left = 144
    Top = 104
    Width = 121
    Height = 21
    TabOrder = 1
    Text = 'cn.bing.com'
  end
  object GetDefaultBrowserButton: TButton
    Left = 280
    Top = 144
    Width = 129
    Height = 25
    Caption = 'Get Default Browser'
    TabOrder = 2
    OnClick = GetDefaultBrowserButtonClick
  end
  object IEVersionButton: TButton
    Left = 280
    Top = 184
    Width = 129
    Height = 25
    Caption = 'Get IE Version'
    TabOrder = 3
    OnClick = IEVersionButtonClick
  end
  object SetKey: TEdit
    Left = 144
    Top = 240
    Width = 121
    Height = 21
    TabOrder = 4
    Text = 'SetKey'
  end
  object SetValue: TEdit
    Left = 144
    Top = 264
    Width = 121
    Height = 21
    TabOrder = 5
    Text = 'SetValue'
  end
  object SetToRedisButton: TButton
    Left = 280
    Top = 264
    Width = 75
    Height = 25
    Caption = 'Set to Redis'
    TabOrder = 6
    OnClick = SetToRedisButtonClick
  end
  object GetFromRedisButton: TButton
    Left = 280
    Top = 344
    Width = 75
    Height = 25
    Caption = 'Get from Redis'
    TabOrder = 7
    OnClick = GetFromRedisButtonClick
  end
  object GetKey: TEdit
    Left = 144
    Top = 344
    Width = 121
    Height = 21
    TabOrder = 8
    Text = 'GetKey'
  end
  object GetValue: TEdit
    Left = 144
    Top = 376
    Width = 121
    Height = 21
    TabOrder = 9
    Text = 'GetValue'
  end
end
