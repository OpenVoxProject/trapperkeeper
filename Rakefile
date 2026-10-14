begin
  require 'github_changelog_generator/task'
rescue LoadError
  task :changelog do
    abort('Run `bundle install --with release` to install the `github_changelog_generator` gem.')
  end
else
  GitHubChangelogGenerator::RakeTask.new :changelog do |config|
    config.header = <<~HEADER.chomp
      # Changelog

      All notable changes to this project will be documented in this file.
    HEADER
    config.user = 'OpenVoxProject'
    config.project = 'trapperkeeper'
    config.exclude_labels = %w[dependencies duplicate question invalid wontfix wont-fix modulesync skip-changelog]
    # this is probably the worst way to do this
    # ideally there would be a VERSION file and clojure and Rake would read it
    config.future_release = File.readlines('project.clj').grep(/trapperkeeper/).first.scan(/".*"/).first.gsub('"', '')
    # we limit the changelog to all new openvox releases, to skip perforce onces
    # otherwise the changelog generate takes a lot amount of time
    config.since_tag = '4.0.2'
    #config.exclude_tags_regex = /\A7\./
    config.release_branch = 'main'
  end
end

namespace :vox do
  desc 'Set the version in project.clj for the release'
  task 'version:bump:full', [:version] do |_, args|
    version = args[:version]
    abort 'You must provide a version.' if version.nil? || version.empty?

    project = File.read('project.clj')
    defproject = /^(\(defproject\s+\S+\s+)"[^"]+"/
    abort 'Could not find the defproject version in project.clj' unless project.match?(defproject)

    File.write('project.clj', project.sub(defproject) { "#{Regexp.last_match(1)}\"#{version}\"" })
    puts "Set the version in project.clj to #{version}"
  end
end

desc 'Prepare for a release'
task 'release:prepare' => [:changelog]
