import {
    Wrap,
    WrapItem,
    Spinner,
    Text
} from '@chakra-ui/react';
import SideBarWithHeader from './shared/SideBar.jsx';
import CardWithImage from './components/Card.jsx'
import { useEffect, useState } from 'react';
import { getCustomers } from './services/client.js';
import CreateCustomerDrawer  from "./components/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";

const App = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchCustomers = () => {
        setLoading(true)
        getCustomers().then(res => {
            setCustomers(res.data )
        }).catch(err => {
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false);
        })
    }


    useEffect(() => {
        fetchCustomers();
    }, []);

    if (loading) {
        return (
            <SideBarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SideBarWithHeader>
        )
    }


    if (customers.length <= 0) {
        return (
            <SideBarWithHeader>
                <CreateCustomerDrawer
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>No Customers Available</Text>
            </SideBarWithHeader>
        )
    }

    return (
        <SideBarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}  >
                        <CardWithImage
                            {...customer}
                            fetchCustomers={fetchCustomers}

                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SideBarWithHeader>
    )
}

export default App;