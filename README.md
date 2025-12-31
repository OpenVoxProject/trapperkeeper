<img src="http://images4.fanpop.com/image/photos/21500000/4x12-Trapper-Keeper-south-park-21568387-720-540.jpg"
 alt="Trapperkeeper logo" title="hold it" align="right" height="300px" />

# Trapperkeeper

Trapperkeeper is a Clojure framework for hosting long-running applications and services.
You can think of it as a sort of "binder" for Ring applications and other modular bits of Clojure code.

## Installation

Add the following dependency to your `project.clj` file:

[![Clojars Project](http://clojars.org/org.openvoxproject/trapperkeeper/latest-version.svg)](http://clojars.org/org.openvoxproject/trapperkeeper)

## Documentation

You can find a quick-start, example code, and lots and lots of documentation in our:

* [Documentation](documentation/Index.md)

## Lein Template

A Leiningen template is available that shows a suggested project structure:

    lein new trapperkeeper my.namespace/myproject

Once you've created a project from the template, you can run it via the lein alias:

    lein tk

Note that the template is not intended to suggest a specific namespace organization;
it's just intended to show you how to write a service, a web service, and tests
for each.

## Related Projects

Here are some additional projects that provide Trapperkeeper services, and
other related functionality:

* [trapperkeeper-webserver-jetty9](https://github.com/openvoxproject/trapperkeeper-webserver-jetty9): a Jetty9-based webserver for use with TK applications
* [trapperkeeper-rpc](https://github.com/puppetlabs/trapperkeeper-rpc): a TK service that allows you to easily build a way to call remote TK services over RPC
* [trapperkeeper-metrics](https://github.com/openvoxproject/trapperkeeper-metrics): a TK service that manages the life cycle of a [MetricRegistry](https://github.com/dropwizard/metrics), so that all of your TK services can register metrics with a common configuration syntax.
* [trapperkeeper-comidi-metrics](https://github.com/openvoxproject/trapperkeeper-comidi-metrics): a TK utility library that provides middleware to automatically generate metrics for all requests to each of your bidi/comidi HTTP routes.
* [trapperkeeper-status](https://github.com/openvoxproject/trapperkeeper-status): a TK service that provides a mechanism for registering status callbacks for all of your other TK services, and web API for requesting status information about the entire TK system.
* [trapperkeeper-scheduler](https://github.com/openvoxproject/trapperkeeper-scheduler): a TK service that provides an API for scheduling periodic background tasks

## License

Copyright © 2013 Puppet Labs
Copyright © 2025 Vox Pupuli

Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Support

GitHub issues and PRs are welcome! Additionally, drop us a line in [the Vox Pupuli Slack](https://voxpupuli.slack.com).
