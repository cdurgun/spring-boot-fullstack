import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from '@chakra-ui/react';
import UpdateCustomerForm from "./UpdateCustomerForm.jsx";

const AddIcon = () => "+";

const CloseIcon = () => "x";



const UpdateCustomerDrawer = ({ fetchCustomers, initialValues, customerId }  ) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            bg={'gray.200'}
            color={'black'}
            rounded={'full'}
            onClick={onOpen}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
        >
            Update customer
        </Button>

        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Update customer</DrawerHeader>

                <DrawerBody>
                    <UpdateCustomerForm
                        fetchCustomers={fetchCustomers}
                        initialValues={initialValues}
                        customerId={customerId}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon />}
                        onClick={onClose}
                        colorScheme={"teal"}

                    >
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
        </>
}

export default UpdateCustomerDrawer;

