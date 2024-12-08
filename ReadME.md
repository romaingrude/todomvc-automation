# <u> Automation Challenge </u>

This automation suite follows [THIS](https://docs.google.com/spreadsheets/d/1xfVrCSVfsmKX8-mgEvxGOfKtDdXz4gK5cTZg8T5Ipso/edit#gid=0) test tracker.

The test suite is configured to run seamlessly across Safari, Chrome, and Firefox browsers. Extensive testing has been conducted on all three browsers to ensure compatibility and reliability. The setup includes a customized configuration within IntelliJ and the gradle.build file, enabling the parallel execution of the test scripts for enhanced efficiency.

This parallel execution capability optimizes the testing process, allowing multiple test scenarios to be executed concurrently across different browsers. The combination of browser compatibility testing and parallel execution ensures a robust and efficient testing environment for the test suite.

Page Object Model and Page Factory techniques have been used to render the test suite more flexible for future test against different frameworks or browsers.

## Continuous Integration with GitHub Actions

This project incorporates a robust Continuous Integration (CI) pipeline utilizing GitHub Actions to automate the execution of test cases. The workflow is triggered automatically on every push or pull request to the `main` branch, ensuring that any new code changes or merges are thoroughly tested. The pipeline is configured to run Gradle commands, performing a clean build followed by executing the entire test suite across multiple browsers, including Chrome and Firefox.

In the event of any test failures, detailed test reports and logs are generated and archived for easy access and review. This CI process helps maintain the integrity of the test suite, verifying that new changes do not introduce regressions or compromise the functionality of the application under test. By automating this testing process, the workflow ensures fast feedback for developers and contributes to a continuous delivery pipeline that is both efficient and reliable.


Added Allure Report with access with Github Pages
