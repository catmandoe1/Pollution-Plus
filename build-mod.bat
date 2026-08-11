@echo off
call gradlew build

del "C:\Users\ben\curseforge\minecraft\Instances\Test 1\mods\PollutionPlus-*.jar"
del "C:\Games\minecraft\minecraft servers\modding tests\mods\PollutionPlus-*.jar"
del "C:\Users\ben\curseforge\minecraft\Instances\pollution plus test profile\mods\PollutionPlus-*.jar"

move /Y "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\*-sources.jar" "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\old"

copy "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\*.jar" "C:\Users\ben\curseforge\minecraft\Instances\Test 1\mods"
copy "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\*.jar" "C:\Games\minecraft\minecraft servers\modding tests\mods"
copy "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\*.jar" "C:\Users\ben\curseforge\minecraft\Instances\pollution plus test profile\mods"

move /Y "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\*.jar" "C:\Games\minecraft\Minecraft_Modding\forge\PullutionPlus_Versions\current\PollutionPlus\build\libs\old"

echo Completed test 1, pollution plus testing curse forge profiles and test server
timeout /t 3 /nobreak