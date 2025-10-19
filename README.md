# 🛡️ HPArmorBar

**HPArmorBar** は、Paper サーバーでプレイヤーのHPと防御力(ARM)を  
常にアクションバーに表示する軽量プラグインです。

## 🎮 機能
- HPと防御力を数値でリアルタイム表示
- アクションバーに常時表示され、UIのハートやアーマーを非表示にしても視認性を確保
- Paper 1.20〜1.21+ 対応
- 超軽量・イベント駆動＋定期更新方式

## 🧩 使い方
1. `HPArmorBar-x.x.jar` を `plugins/` に入れる
2. サーバーを再起動する
3. 完了！ アクションバーに数値が表示されます

> 💡 透明HUDを使いたい場合は、  
> [TransparentHUD リソースパック](https://github.com/Bluehat-R/TransparentHUD) を一緒に導入してください！

## ⚙️ 動作環境
- PaperMC 1.20.x〜1.21.x
- Java 17 以上

## 📦 ビルド
Maven でビルド可能です：
```bash
mvn clean package
```
生成された .jar は target/ に出力されます。

🪪 ライセンス

このプラグインは MIT License
のもとで公開されています。
自由に利用・改変・再配布可能です。