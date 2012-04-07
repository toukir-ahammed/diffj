#!/usr/bin/jruby -w
# -*- ruby -*-

require 'type/field/variable/renamed/tc'

module DiffJ::Type::Field::Variable::Renamed
  class InitTestCase < TestCase
    def test_unchanged
      run_test 'Unchanged'
    end

    def test_changed
      run_test 'Changed', changed("d", "dbl", loc(2, 12), loc(3, 14))
    end
  end
end
