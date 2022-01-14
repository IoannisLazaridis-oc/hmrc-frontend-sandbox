# Energy Source Checker

The purpose of this exercise is to create an application where given a postcode and a time range, it will give you a report detailing how green the energy supplied to the specified postcode during the given time range was.

## Technical specification

### The Journey to be created

Follow HMRC standards and stick to one input per page, make sure you give the user a view of their answers before submitting and the opportunity to change them (Check your answers page). The journey will look something like this:

`What is your postcode? -> Start Date -> End Date -> Check Your Answers -> Result`

### Styling

Follow GOV.UK styling, they have a design system that is tremendously useful. Feel free to use it [here](https://design-system.service.gov.uk/).

### API to integrate with

We will be integrating with [Carbon Intensity](https://carbonintensity.org.uk/), an API developed by the National Grid that offers visibility on a wide range of parameters to do with Carbon Generation in the UK.

You can find their offering [here](https://api.carbonintensity.org.uk/) and we will be integrating with this API specifically: 

`GET /regional/intensity/{from}/{to}/postcode/{postcode}`

You can find the documentation for the selected API [here](https://carbon-intensity.github.io/api-definitions/#get-regional-intensity-from-to-postcode-postcode). In a nutshell, given a postcode and a time range you will receive a list of all the different energy sources that supplied energy to that location during the given timeframe, together with the overall percentage of each energy type.

### Report to generate

- The report will display a GOV.UK [panel](https://design-system.service.gov.uk/), it will be coloured as such:
  - Green, with the text `Most of your energy came from a renewable source` if the majority of sources were renewable.
  - Amber, with the text `Most of your energy came from a nuclear source` if the majority of sources were nuclear.
  - Red, with the text `Most of your energy came from a non renewable source` for any other source.
- Underneath the GOV.UK panel, there will be a breakdown of all the sources.


