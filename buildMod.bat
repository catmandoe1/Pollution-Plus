@echo off
call gradlew build

del "C:\Users\ben\curseforge\minecraft\Instances\testing pp 1.20.1\mods\pollutionplus-*.jar"

copy "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\1.20.1\build\libs\pollutionplus*.jar" "C:\Users\ben\curseforge\minecraft\Instances\testing pp 1.20.1\mods"

echo built mod and updated mod in "testing pp 1.20.1" curseforge profile
timeout /t 3 /nobreak