object Form1: TForm1
  Left = 280
  Top = 145
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
end
