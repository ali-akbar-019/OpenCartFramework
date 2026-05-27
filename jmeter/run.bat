@echo off
echo ========================================
echo JMeter - Run All Tests and Generate Report
echo ========================================
echo.

cd /d D:\eclipse-workspace\OpenCartFramework\jmeter

echo Deleting old results...
if exist all_tests.jtl del all_tests.jtl
if exist combined-report rmdir /s /q combined-report
echo Done.
echo.

echo Running Homepage Load Test...
jmeter -n -t "OpenCart Homepage Load Test.jmx" -l all_tests.jtl
echo.

echo Running Login Stress Test...
jmeter -n -t "OpenCart Login Stress Test.jmx" -l all_tests.jtl
echo.

echo Running Search Performance Test...
jmeter -n -t "OpenCart Search Performance Test.jmx" -l all_tests.jtl
echo.

echo Generating Combined HTML Report...
jmeter -g all_tests.jtl -o combined-report
echo.

echo ========================================
echo Done! Opening report...
echo ========================================

start combined-report\index.html