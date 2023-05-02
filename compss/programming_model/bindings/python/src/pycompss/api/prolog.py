#!/usr/bin/python
#
#  Copyright 2002-2022 Barcelona Supercomputing Center (www.bsc.es)
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

# -*- coding: utf-8 -*-

"""
PyCOMPSs API - Prolog decorator.

This file contains the Prolog class, needed for the task prolog definition
through the decorator.
"""

from functools import wraps

from pycompss.util.context import CONTEXT
from pycompss.api.commons.constants import INTERNAL_LABELS
from pycompss.api.commons.constants import LABELS
from pycompss.api.commons.decorator import keep_arguments
from pycompss.api.commons.decorator import resolve_fail_by_exit_value
from pycompss.api.commons.decorator import CORE_ELEMENT_KEY
from pycompss.runtime.task.definitions.core_element import CE
from pycompss.util.arguments import check_arguments
from pycompss.util.typing_helper import typing

if __debug__:
    import logging

    logger = logging.getLogger(__name__)

MANDATORY_ARGUMENTS = {LABELS.binary}
SUPPORTED_ARGUMENTS = {
    LABELS.binary,
    LABELS.args,
    LABELS.fail_by_exit_value,
}
DEPRECATED_ARGUMENTS = set()  # type: typing.Set[str]


class Prolog:  # pylint: disable=too-few-public-methods
    """Prolog decorator class.

    If defined, will execute the binary before the task execution on the
    worker.
    Should always be added on top of the 'task' definition.
    """

    __slots__ = [
        "decorator_name",
        "args",
        "kwargs",
        "scope",
        "core_element",
        "core_element_configured",
    ]

    def __init__(self, *args: typing.Any, **kwargs: typing.Any) -> None:
        """Store arguments passed to the decorator.

        self = itself.
        args = not used.
        kwargs = dictionary with the given prolog arguments.

        :param args: Arguments
        :param kwargs: Keyword arguments
        """
        decorator_name = "".join(("@", Prolog.__name__.lower()))
        # super(Prolog, self).__init__(decorator_name, *args, **kwargs)
        self.decorator_name = decorator_name
        self.args = args
        self.kwargs = kwargs
        self.scope = CONTEXT.in_pycompss()
        self.core_element = None  # type: typing.Optional[CE]
        self.core_element_configured = False
        if self.scope:
            # Check the arguments
            check_arguments(
                MANDATORY_ARGUMENTS,
                DEPRECATED_ARGUMENTS,
                SUPPORTED_ARGUMENTS | DEPRECATED_ARGUMENTS,
                list(kwargs.keys()),
                decorator_name,
            )

    def __call__(self, user_function: typing.Callable) -> typing.Callable:
        """Call Prolog simply updates the CE and saves Prolog parameters.

        :param user_function: User function to be decorated.
        :return: Decorated dummy user function.
        """

        @wraps(user_function)
        def prolog_f(*args: typing.Any, **kwargs: typing.Any) -> typing.Any:
            if not self.scope:
                raise NotImplementedError

            if __debug__:
                logger.debug("Executing prolog wrapper.")

            if (
                CONTEXT.in_master() or CONTEXT.is_nesting_enabled()
            ) and not self.core_element_configured:
                self.__configure_core_element__(kwargs)

            with keep_arguments(args, kwargs, prepend_strings=True):
                # Call the method
                ret = user_function(*args, **kwargs)

            return ret

        prolog_f.__doc__ = user_function.__doc__
        return prolog_f

    def __configure_core_element__(self, kwargs: dict) -> None:
        """Include the registering info related to @prolog.

        IMPORTANT! Updates self.kwargs[CORE_ELEMENT_KEY].

        :param kwargs: Keyword arguments received from call.
        :return: None
        """
        if __debug__:
            logger.debug("Configuring @prolog core element.")

        # Resolve the fail by exit value
        resolve_fail_by_exit_value(self.kwargs)

        binary = self.kwargs[LABELS.binary]
        prolog_args = self.kwargs.get(LABELS.args, INTERNAL_LABELS.unassigned)
        fail_by = self.kwargs.get(LABELS.fail_by_exit_value)
        _prolog = [binary, prolog_args, fail_by]

        core_element = kwargs.get(CORE_ELEMENT_KEY, CE())
        core_element.set_impl_prolog(_prolog)
        kwargs[CORE_ELEMENT_KEY] = core_element
        # Set as configured
        self.core_element_configured = True


# ########################################################################### #
# ################### PROLOG DECORATOR ALTERNATIVE NAME ##################### #
# ########################################################################### #

prolog = Prolog  # pylint: disable=invalid-name
