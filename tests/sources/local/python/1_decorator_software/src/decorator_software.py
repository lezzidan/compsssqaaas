#!/usr/bin/python

# -*- coding: utf-8 -*-

"""
PyCOMPSs Testbench
========================
"""

# Imports
import unittest

from modules.testSoftwareDecorator import TestSoftwareDecorator


def main():
    suite = unittest.TestLoader().loadTestsFromTestCase(TestSoftwareDecorator)
    unittest.TextTestRunner(verbosity=2).run(suite)


if __name__ == "__main__":
    main()
