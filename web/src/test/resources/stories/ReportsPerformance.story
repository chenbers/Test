Meta:
@page login

Narrative:

Scenario: TC6213 : Driver Performance Key Metrics Date Range is present
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
Then I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present

Scenario: TC6215 : Driver Performance Key Metrics - Today
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6216 : Driver Performance Key Metrics - Yesterday
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM yesterday is present in Date Range start
And I validate 11:59PM yesterday is present in Date Range end

Scenario: TC6217 : Driver Performance Key Metrics - Sun to Sat
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6218 : Driver Performance Key Metrics - Past 7 days
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6219 : Driver Performance Key Metrics - Last Month
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6220 : Driver Performance Key Metrics - Past 30 days
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6221 : Driver Performance Key Metrics - Past 3 Months
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6222 : Driver Performance Key Metrics - Past 6 Months
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC6223 : Driver Performance Key Metrics - Past Year
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

*************************************************************************************

Scenario: TC6214 : Driver Performance Key Metrics RYG Date Range is present
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
Then I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present

Scenario: TC : Driver Performance Key Metrics RYG - Today
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Yesterday
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM yesterday is present in Date Range start
And I validate 11:59PM yesterday is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Sun to Sat
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Past 7 days
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Last Month
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Past 30 days
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Past 3 Months
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Past 6 Months
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end

Scenario: TC : Driver Performance Key Metrics RYG - Past Year
Given I am logged in
And I click the Reports link
And I click the Performance link
When I select "Driver Performance Key Metrics RYG" from the Report dropdown
And I validate the Date Range start textfield is present
And I validate the Date Range end textfield is present
And I select "Today" from the Time Frame dropdown
Then I validate 12:00AM today is present in Date Range start
And I validate 11:59PM today is present in Date Range end